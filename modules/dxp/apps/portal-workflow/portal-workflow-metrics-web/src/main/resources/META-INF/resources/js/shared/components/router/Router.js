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
		const url = String(document.location);

		this.state = {
			firstUrl: url,
			lastUrl: url,
			path: props.defaultPath,
			query: {}
		};
	}

	componentDidMount() {
		const onPageChanged = () => this.onPageChanged();

		window.addEventListener(PAGE_CHANGE, onPageChanged);

		this.unsub = () => {
			window.removeEventListener(PAGE_CHANGE, onPageChanged);
		};
	}

	componentWillUnmount() {
		this.unsub();
		this.unsub = null;
	}

	getQuery() {
		const url = String(document.location);

		return url
			.substring(url.lastIndexOf('#') + 1)
			.split('&')
			.filter(key => key !== '')
			.reduce((query, pairKey) => {
				const splitedKey = pairKey.split('=');

				query[splitedKey[0]] = splitedKey[1];

				return query;
			}, {});
	}

	onPageChanged() {
		const query = this.getQuery();

		const {_path: path} = query;
		let {_title: title} = query;

		const {defaultPath, paths} = this.props;
		const {lastUrl} = this.state;

		const isFirstPage = !path || path === defaultPath;

		const pathToFind = isFirstPage ? defaultPath : path;

		const componentRender = paths.find(router => router.path === pathToFind);

		if (componentRender) {
			if (!title) {
				({title} = componentRender);
			}

			if (isFirstPage) {
				controlMenuHeading({title});
			}
			else {
				controlMenuHeading({backPath: lastUrl, title});
			}
		}

		delete query['_path'];
		delete query['_title'];

		this.setState({
			lastUrl: String(document.location),
			path: pathToFind,
			query
		});
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