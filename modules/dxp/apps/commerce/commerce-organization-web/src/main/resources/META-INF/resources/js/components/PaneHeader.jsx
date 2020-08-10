import React, {Component} from 'react';
import PropTypes from 'prop-types';

import PaneOrgInfo from './PaneOrgInfo';
import PaneViewSelector from './PaneViewSelector';
import PaneSearchBar from './PaneSearchBar';
import { bindAll } from '../utils/utils.es';

class PaneHeader extends Component {
	constructor(props) {
		super(props);

		this.state = {
			showMenu: false
		};

		bindAll(
			this,
			'hideMenu',
			'showMenu'
		);
	}

	hideMenu(e) {
		this.setState(() => ({
			showMenu: false
		}))
	}

	showMenu() {
		this.setState(state => ({
			showMenu: !state.showMenu
		}))
	}

	render() {
		const {
			orgName,
			totalSubOrg,
			listBy,
			onLookUp,
			onViewSelected,
			totalAccounts,
			totalUsers,
			spritemap,
			colorIdentifier
		} = this.props;

		return (
			<div className='pane-header'>
				<PaneOrgInfo
					orgName={orgName}
					childrenNo={totalSubOrg}
					colorIdentifier={colorIdentifier}
					showMenu={this.showMenu}
				/>

				<PaneViewSelector
					listBy={listBy}
					totalAccounts={totalAccounts}
					totalUsers={totalUsers}
					onViewSelected={onViewSelected}
				/>

				<PaneSearchBar
					onLookUp={onLookUp}
					spritemap={spritemap}
				/>
			</div>
		);
	}
}

export default PaneHeader;
