import React, {Component} from 'react';
import PropTypes from 'prop-types';
import * as d3 from 'd3';
import getCN from 'classnames';
import w from '../utils/window.es';

import {
    noop,
    isNumber,
    isArray,
    truncateTextNode,
    bindAll,
    getLocalizedText
} from '../utils/utils.es';

class OrgChart extends Component {
    constructor(props) {
        super(props);

        bindAll(
            this,
            'collapse',
            'handleNodeClick',
            'zoomIn',
            'zoomOut'
        );

        this._nodeLabelWidth = 5;
        this._currentScale = 0.88;
        this._duration = 400;
        this._idCount = 0;
        this._margin = 24;
        this._nodeCenterOffset = 200;
        this._nodeDepth = 400;
        this._nodeHeight = 72;
        this._nodePadding = 10;
        this._nodeWidth = 310;
        this._selectedIdPath = [props];
        this._svgHeight = '100%';
        this._svgWidth = '100%';
    }

    shouldComponentUpdate() {
        return false;
    }

    componentDidMount() {
        const {data, namespace, selectedId} = this.props;

        this._namespace = namespace.toString();

        if (data) {
            this._svg = this.createSVG();
            this._tree = this.setUpTree();

            this._root = this.setUpRoot(data);

            this.updateChart(this._root);

            const nodes = this._tree(this._root).descendants(),
                selectedNode = nodes.find(
                    d => d.data.organizationId === selectedId
                );

            this.updateChart(selectedNode);

            setTimeout(() => {
                const orgNodes = document.querySelectorAll('[data-organization-id]'),
                    firstOrgNode = orgNodes[orgNodes.length - 2],
                    mouseEvent = new MouseEvent('click');

                firstOrgNode.dispatchEvent(mouseEvent);
            }, 1000);
        }
    }

    render() {
        return (
            <div className='org-chart-container'>
                <div className='svg-wrapper'>
                    <svg ref='chartSVG'>
                        <g ref='tree'/>
                    </svg>
                </div>

                <div className='controls'>
                    <div className='zoom'>
                        <span className='zoom-button' onClick={this.zoomOut}>&ndash;</span>
                        <span className='zoom-percentage' ref='zoomPercent'>{'100%'}</span>
                        <span className='zoom-button' onClick={this.zoomIn}>+</span>
                    </div>
                </div>
            </div>
        );
    }

    addNewNode(newData, parentNode) {
        const newNode = d3.hierarchy(newData, d => d.children);

        newNode.parent = parentNode;
        newNode.depth = parentNode.depth + 1;

        if (newNode.children) {
            newNode._children = newNode.children.map(
                child => ({
                    ...child,
                    depth: parentNode.depth + 2
                })
            );

            newNode.children = null;
        }

        if (!isArray(parentNode._children)) {
            parentNode._children = [];
        }

        parentNode._children = [...parentNode._children, newNode];
    }

    collapse(item) {
        if (item.children) {
            item._children = item.children;

            item._children.forEach(this.collapse);

            item.children = null;
        }
    }

    createConnectingLine(s, u, node) {
        const y = u.y + this._nodeWidth,
            isRoot = node.parent
                && node.parent.data
                && node.parent.data.name === 'Root';

        return isRoot ? '' :
            `M ${s.y} ${s.x} C ${(s.y + y) / 2} ${s.x}, ${(s.y + y) / 2} ${u.x}, ${y} ${u.x}`;
    }

    createNewNodes(nodeEnter, source) {
        const gNode = nodeEnter.append('g').lower();

        gNode.attr('class', 'node');
        gNode.attr('transform', () => this.getTranslateString(source.y0, source.x0));
        gNode.attr('data-color-identifier', this.getColorIdentifier);
        gNode.attr('data-organization-id', ({data}) => {
            const {organizationId} = data;

            return organizationId;
        });
        gNode.on('click', this.handleNodeClick);

        const rectNode = gNode.append('rect');
        const nodeHeightHalf = this._nodeHeight / 2;

        rectNode.attr('height', this._nodeHeight);
        rectNode.attr('width', this._nodeWidth);
        rectNode.attr('y', -nodeHeightHalf);

        const nodeLabel = gNode.append('line');

        nodeLabel.attr('class', 'node-label');
        nodeLabel.attr('height', this._nodeHeight - 10);
        nodeLabel.attr('width', this._nodeLabelWidth);
        nodeLabel.attr('x1', this._nodePadding);
        nodeLabel.attr('y1', (this._nodePadding - nodeHeightHalf));
        nodeLabel.attr('x2', this._nodePadding);
        nodeLabel.attr('y2', (nodeHeightHalf - this._nodePadding));
        nodeLabel.attr('stroke', this.getColorIdentifier);

        const nameNode = gNode.append('text'),
            textRight = this._nodePadding + this._nodeLabelWidth + 15;

        nameNode.attr('class', 'name');
        nameNode.attr('x', textRight);
        nameNode.attr(
            'y',
            ({data}) => {
                const {organizationsTotal} = data;

                return !!organizationsTotal ? -5 : 5;
            }
        );
        nameNode.text(({data}) => {
            const isRoot = data.name === 'Root';

            if (isRoot) {
                nameNode.node().parentNode.classList.add('root-element');
            }

            return data.name;
        });
        nameNode.each(truncateTextNode);

        const secondaryInfoNode = gNode.append('text');

        secondaryInfoNode.attr('class', 'secondary-info');
        secondaryInfoNode.attr('x', textRight);
        secondaryInfoNode.attr('y', 16);
        secondaryInfoNode.text(
            ({data}) => {
                const {organizationsTotal} = data;
                const label = 'suborganizations';

                return !!organizationsTotal ?
                    `${organizationsTotal} ${getLocalizedText(label)}` : '';
            }
        );

        const addButtonNodeGroup = gNode.append('g');

        addButtonNodeGroup
            .attr('class', 'add-button-node')
            .on('click', (d) => {
                const {organizationId} = d.data || {},
                    addOrganizationButton = document.querySelector(`#${this._namespace}addOrganizationButton`),
                    cmd = 'add',
                    mouseEvent = new CustomEvent('click', {
                        detail: {
                            organizationId,
                            cmd
                        }
                    });

                addOrganizationButton.dispatchEvent(mouseEvent);
            });

        addButtonNodeGroup.append('circle')
            .attr('class', 'add-button-node-circle')
            .attr('r', 15)
            .attr('cx', this._nodeWidth - 10 - 30);

        addButtonNodeGroup.append('text')
            .attr('class', 'add-button-node-plus')
            .attr('x', this._nodeWidth - this._nodePadding - 35)
            .attr('y', nodeHeightHalf * 0.18)
            .text('+');

        return gNode;
    }

    createSVG() {
        const {chartSVG, tree} = this.refs;

        const height = this._svgHeight;
        const width = this._svgWidth;

        const svg = d3.select(chartSVG);

        svg.attr('class', 'org-chart');
        svg.attr('height', height);
        svg.attr('viewBox', null);
        svg.attr('width', width);

        const treeNode = d3.select(tree);

        treeNode.attr('transform', this.getTranslateString(this._margin, this._nodeHeight * 2));

        this._zoom = d3.zoom().scaleExtent([0.25, 2]).on(
            'zoom',
            () => this.handleZoom(svg, treeNode)
        );

        this._zoomInterface = d3.select(chartSVG);

        this._zoomInterface.call(this._zoom);

        return treeNode;
    }

    getTranslateString(x, y) {
        return `translate(${x}, ${y})`;
    }

    handleNodeClick(d) {
        const {
            onNodeClick,
            requestChildren
        } = this.props;

        const {
            data = {},
            _children,
            children
        } = d;

        const {
            organizationId,
            lastLevel,
            colorIdentifier
        } = data;

        const lastActive = this._selectedIdPath[this._selectedIdPath.length - 1] === organizationId;

        this._selectedIdPath = this.setSelectedId(d);

        onNodeClick(isNumber(organizationId) ? organizationId : undefined, colorIdentifier);

        if (!(children || _children) && !lastLevel) {
            requestChildren(organizationId).then(subOrganizations => {
                if (isArray(subOrganizations)) {
                    subOrganizations.forEach(
                        item => this.addNewNode(item, d)
                    );
                }

                this.updateChildren(d, lastActive);
            });
        } else {
            this.updateChildren(d, lastActive);
        }
    }

    handleNodesEntering(node, source) {
        return this.createNewNodes(node.enter(), source);
    }

    handleNodesExiting(node, source) {
        const exitNode = node.exit();

        exitNode.transition().duration(this._duration).attr(
            'transform',
            () => this.getTranslateString(source.y, source.x)
        ).remove();
    }

    handleNodesUpdating(nodeEnter, node) {
        const nodeUpdating = nodeEnter.merge(node);

        const rectNodes = nodeUpdating.selectAll('rect');

        rectNodes.attr(
            'class',
            d => getCN(
                'node',
                {
                    highlight: this._selectedIdPath[d.depth] === d.data.organizationId,
                    selected: this._selectedIdPath[this._selectedIdPath.length - 1] === d.data.organizationId
                }
            )
        );

        nodeUpdating.transition().duration(this._duration).attr(
            'transform',
            ({x, y}) => this.getTranslateString(y, x)
        );
    }

    handleZoom(svg, g) {
        const {k} = d3.event.transform;

        this._currentScale = k;

        this.updateZoomValue();

        g.attr('transform', d3.event.transform);
    }

    setOldPositions(nodes) {
        nodes.forEach(
            d => {
                d.x0 = d.x;
                d.y0 = d.y;
            }
        );
    }

    getColorIdentifier(d) {
        if (!!d.data.organizationId) {
            const colorIdentifier = 'colorIdentifier' in d.data ?
                d.data.colorIdentifier : d.parent.data.colorIdentifier;

            d.data['colorIdentifier'] = colorIdentifier;

            return colorIdentifier;
        }

        return '';
    }

    setSelectedId(node, arr = []) {
        const {parent} = node;

        if (parent) {
            arr = this.setSelectedId(parent, arr);
        }

        return [...arr, node.data.organizationId];
    }

    setUpRoot(data) {
        const root = d3.hierarchy(data, d => d.organizations);

        const {clientHeight, clientWidth} = this.refs.chartSVG;

        root.x0 = clientHeight / 2;
        root.y0 = (clientWidth / 2) - this._nodeCenterOffset - (this._nodeWidth * 3);

        return root;
    }

    setUpTree() {
        return d3.tree().nodeSize([this._nodeHeight + this._margin, this._nodeWidth]);
    }

    updateChart(source) {
        const treeData = this._tree(this._root);
        const nodes = treeData.descendants();

        nodes.forEach(
            d => {
                d.y = d.depth * this._nodeDepth;
            }
        );

        const gNodes = this._svg.selectAll('g.node').data(nodes, d => d.id || (d.id = ++this._idCount));
        const nodeEnter = this.handleNodesEntering(gNodes, source);

        this.handleNodesUpdating(nodeEnter, gNodes);
        this.handleNodesExiting(gNodes, source);
        this.updateLinks(source, treeData);
        this.setOldPositions(nodes);
        this.updateTransform(source);
    }

    updateChildren(d, collapse) {
        if (d.children && collapse) {
            d._children = d.children;
            d.children = null;
        } else if (d._children) {
            d.children = d._children;
            d._children = null;
        }

        this.updateChart(d);
    }

    updateLinks(source, treeData) {
        const links = treeData.descendants().slice(1);
        const linkNodes = this._svg.selectAll('path.link').data(links, d => d.id);
        const gNode = linkNodes.enter().insert('path', 'g');

        gNode.attr(
            'd',
            d => {
                const o = {x: source.x0, y: source.y0};

                return this.createConnectingLine(o, o, d);
            }
        );

        const linkUpdate = gNode.merge(linkNodes);

        linkUpdate.attr(
            'class',
            d => getCN(
                'link',
                {
                    highlight: this._selectedIdPath[d.depth] === d.data.organizationId
                }
            )
        );

        linkUpdate.transition().duration(this._duration).attr(
            'd',
            d => this.createConnectingLine(d, d.parent, d)
        );

        const exitNode = linkNodes.exit();

        exitNode.transition().duration(this._duration).attr(
            'd',
            d => {
                const o = {x: source.x, y: source.y};

                return this.createConnectingLine(o, o, d);
            }
        );

        exitNode.remove();
    }

    updateTransform(source) {
        const {height, width} = this.refs.chartSVG.getBoundingClientRect();

        const k = this._currentScale;
        const offset = this._nodeCenterOffset + this._nodeWidth;

        let y0 = source.y0;

        if (!source.children && source.parent) {
            y0 = source.parent.y0;
        }

        const x = -(y0 + offset) * k + width / 2;
        const y = -source.x0 * k + (height / 2 - 20);

        this._svg.transition().duration(this._duration).attr(
            'transform',
            `${this.getTranslateString(x, y)} scale(${k})`
        );

        this._zoomInterface.transition().duration(this._duration).call(
            this._zoom.transform,
            d3.zoomIdentity.translate(x, y).scale(k)
        );
    }

    updateZoomValue() {
        this.refs.zoomPercent.innerHTML = `${Math.round(this._currentScale * 100)}%`;
    }

    zoomIn() {
        this._zoomInterface.transition().duration(this._duration).call(this._zoom.scaleBy, 2);
    }

    zoomOut() {
        this._zoomInterface.transition().duration(this._duration).call(this._zoom.scaleBy, 0.5);
    }
}

OrgChart.defaultProps = {
    data: {},
    requestChildren: noop
};

OrgChart.propTypes = {
    data: PropTypes.oneOfType([PropTypes.object, PropTypes.array]),
    requestChildren: PropTypes.func,
    selectedId: PropTypes.number
};

export default OrgChart;
