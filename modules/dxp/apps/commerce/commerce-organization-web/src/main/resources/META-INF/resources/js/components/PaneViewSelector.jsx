import React, { Component } from 'react';
import { LIST_BY } from '../utils/constants.es';

const {
	USERS,
	ACCOUNTS
} = LIST_BY;

function isSelected(listBy, callee) {
	return listBy === callee ? 'selected-pane': '';
}

function Tab(props) {
	const {
		viewName,
		onViewSelected,
		totalMembers,
		listBy
	} = props;

	return(
		<span
			className={!totalMembers ? 'disabled' : isSelected(listBy, viewName)}
			onClick={!totalMembers ? null : onViewSelected.bind(this, viewName)}
			role='button' tabIndex='-1'>
			{`${viewName} (${totalMembers})`}
		</span>
	);
}

class PaneViewSelector extends Component {
	render() {
		const { totalAccounts, totalUsers, onViewSelected, listBy } = this.props;

		return(
			<div className='pane-list-selector'>
				{
					[USERS, ACCOUNTS].map((viewName, i) => {
						const totalMembers = viewName === USERS ?
							totalUsers : totalAccounts;

						return (
							<Tab
								key={i}
								viewName={viewName}
								onViewSelected={onViewSelected}
								totalMembers={totalMembers}
								listBy={listBy}
							/>
						);
					})
				}
			</div>
		);
	}
}

export default PaneViewSelector;
