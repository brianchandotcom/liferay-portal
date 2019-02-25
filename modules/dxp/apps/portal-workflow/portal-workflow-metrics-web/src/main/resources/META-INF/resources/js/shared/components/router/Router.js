import 'custom-event-polyfill';
import controlMenuHeading from '../control-menu-heading/controlMenuHeading';
import {PAGE_CHANGE} from './RouterConstants';
import React from 'react';

/**
 * Router.
 * @extends React.Component
 */
export default class Router extends React.Component {
	constructor(props) {
		super(props);
		this.state = {path: props.defautPath, query: {}};
	}

	componentWillUnmount() {
		if (this.unsub) {
			this.unsub();
			this.unsub = null;
		}
	}

	componentDidMount() {
		const changePage = ({detail: {path, query, title}}) => {
			const {defautPath, paths} = this.props;
			const {path: lastPath} = this.state;
			const isFirstPage = path === defautPath;
			const pathToFind = isFirstPage ? defautPath : path;
			const componentRender = paths.find(router => router.path === pathToFind);

			if (componentRender) {
				if (!title) {
					({title} = componentRender);
				}
				if (isFirstPage) {
					controlMenuHeading({title});
				} else {
					controlMenuHeading({backPath: lastPath, title});
				}
			}

			this.setState({path, query});
		};

		document.addEventListener(PAGE_CHANGE, changePage.bind(this));

		this.unsub = () => {
			document.removeEventListener(PAGE_CHANGE, changePage.bind(this));
		};
	}
	render() {
		const {paths} = this.props;
		const {path, query} = this.state;
		const componentRenderIndex = paths.findIndex(
			router => router.path === path
		);

		return (
			<div>
				{componentRenderIndex > -1 &&
					paths[componentRenderIndex].component(query)}
			</div>
		);
	}
}