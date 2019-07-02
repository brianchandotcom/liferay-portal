import React, {Fragment} from 'react';
import HeaderMenuBackItem from './HeaderMenuBackItem';
import HeaderTitle from './HeaderTitle';

export default class HeaderController extends React.Component {
	componentWillMount() {
		const {namespace} = this.props;

		const headerContainer = document.getElementById(
			`${namespace}controlMenu`
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
		const {basePath, title} = this.props;

		return (
			<Fragment>
				<HeaderMenuBackItem
					basePath={basePath}
					container={this.backButtonContainer}
				/>

				<HeaderTitle container={this.titleContainer} title={title} />
			</Fragment>
		);
	}
}
