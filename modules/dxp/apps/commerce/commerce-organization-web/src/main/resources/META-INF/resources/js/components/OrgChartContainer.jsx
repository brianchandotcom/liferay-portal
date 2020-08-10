import React, {Component} from 'react';
import {
    callApi,
    setupDataset,
    bindAll
} from '../utils/utils.es';

import OrgChart from './OrgChart';
import MembersPane from './MembersPane';

class OrgChartContainer extends Component {
    constructor(props) {
        super(props);

        bindAll(
            this,
            'handleNodeClick',
            'setSelection',
            'handleInitialLoad'
        );

        const apiParameters = {
            baseURL: props.apiURL
        };

        this.$didMount = new Promise(resolve =>
            callApi(apiParameters)
                .then(data => {
                    this.setState(() => {
                        return {
                            rootData: setupDataset(data),
                            selectedId: 0
                        };
                    }, () => {
                        resolve(true);
                    });
                })
        )
    }

    handleInitialLoad() {
        this.setState(() => {
            return {loading_: false};
        });
    }

    handleNodeClick(id) {
        return callApi({baseURL: this.props.apiURL, id})
            .then(({organizations}) =>
                organizations.length ? organizations : null
            );
    }

    setSelection(id, colorIdentifier) {
        this.setState(() => {
            return {selectedId: id, colorIdentifier}
        });
    }

    render() {
        const {
            apiURL,
            spritemap,
            imagesPath,
            namespace
        } = this.props;

        const {
            selectedId,
            rootData,
            colorIdentifier
        } = this.state || {};

        return (
            <div className="organization-network">
                {!!rootData &&
                <OrgChart
                    data={rootData}
                    namespace={namespace}
                    onNodeClick={this.setSelection}
                    requestChildren={this.handleNodeClick}
                    selectedId={selectedId}
                />
                }

                {!!selectedId &&
                <MembersPane
                    id={selectedId}
                    apiURL={apiURL}
                    spritemap={spritemap}
                    imagesPath={imagesPath}
                    colorIdentifier={colorIdentifier}
                />
                }
            </div>
        );
    }
}

export default OrgChartContainer;
