import HeaderMenuBackItem from './HeaderMenuBackItem';
import HeaderTitle from './HeaderTitle';
import React from 'react';

export default class HeaderController extends React.Component {
	componentWillMount() {
		const headerContainer = document.getElementById(
			`${Liferay.ControlMenu['_namespace']}controlMenu`
		);

		if (headerContainer) {
			this.backButtonContainer = headerContainer.querySelector(
				'.sites-control-group .control-menu-nav'
			);
			this.titleContainer = headerContainer.querySelector(
				'.tools-control-group .control-menu-level-1-heading'
			);
		}
	}

	render() {
		const { basePath, title } = this.props;

		return (
			<template>
				<HeaderMenuBackItem
					basePath={basePath}
					container={this.backButtonContainer}
				/>

				<HeaderTitle container={this.titleContainer} title={title} />
			</template>
		);
	}
}