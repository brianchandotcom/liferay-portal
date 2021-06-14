/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {LIST_BY} from '../utils/constants';
import {bindAll, callApi} from '../utils/utils';
import MembersList from './MembersList';
import PaneHeader from './PaneHeader';

const {USERS} = LIST_BY;

let membersListCopy = [];

function fetchMembers(apiURL, orgId, listBy, q = '') {
	const collectionPath = listBy + 's',
		apiParams = {
			baseURL: apiURL,
			id: orgId,
		},
		apiParamsMembers = {
			...apiParams,
			path: collectionPath,
			queryParams: {
				page: 1,
				pageSize: 100,
				q,
			},
		};

	return Promise.all([callApi(apiParams), callApi(apiParamsMembers)]).then(
		(data) => {
			const [orgData, membersData] = data;

			const {
				accountsTotal,
				name: orgName,
				organizationsTotal: totalSubOrg,
				usersTotal,
			} = orgData;

			return {
				id: orgId,
				members: membersData[collectionPath],
				orgName,
				totalAccounts: accountsTotal,
				totalSubOrg,
				totalUsers: usersTotal,
			};
		}
	);
}

function filterMembers(name, members) {
	return members.filter((member) =>
		member.name.toLowerCase().includes(name.toLowerCase())
	);
}

class MembersPane extends Component {
	constructor(props) {
		super(props);

		this.state = {
			isLoading: true,
			listBy: USERS,
			searchQuery: '',
		};

		bindAll(this, 'handleListBy', 'handleLookUp', 'handleUpdate');
	}

	componentDidMount() {
		const {id} = this.props;
		const {listBy} = this.state;

		this.$didUpdate = this.handleUpdate(id, listBy);
	}

	componentDidUpdate(prevProps, prevState) {
		const {id} = this.props;
		const {listBy} = this.state;

		if (id !== prevProps.id || listBy !== prevState.listBy) {
			this.setState(
				() => ({isLoading: true}),
				() => {
					this.$didUpdate = this.handleUpdate(id, listBy);
				}
			);
		}
	}

	handleListBy(listBy) {
		this.setState(() => ({
			listBy,
		}));
	}

	handleLookUp(event) {
		const name = event.target.value,
			{apiURL, id} = this.props,
			{listBy} = this.state,
			fromState =
				!!name && name.length
					? filterMembers(name, this.state.members)
					: membersListCopy;

		if (fromState.length) {
			return new Promise((resolve) => {
				this.setState(
					() => ({
						members: fromState,
					}),
					() => {
						resolve(true);
					}
				);
			});
		}
		else {
			return fetchMembers(apiURL, id, listBy, name).then(
				({total, users}) => {
					this.setState(() => {
						if (total) {
							const fromFetch = filterMembers(name, users);

							return fromFetch.length
								? {members: fromFetch}
								: {members: membersListCopy};
						}

						return {members: membersListCopy};
					});
				}
			);
		}
	}

	handleUpdate(id, listBy) {
		const {apiURL} = this.props;

		return fetchMembers(apiURL, id, listBy).then((data) => {
			this.setState(
				(state) =>
					Object.assign(state, data, {
						isLoading: false,
					}),
				() => {
					membersListCopy = this.state.members;
				}
			);
		});
	}

	render() {
		const {
				listBy,
				members,
				orgName,
				totalAccounts,
				totalSubOrg,
				totalUsers,
			} = this.state,
			paneClasses = 'pane pane-open';

		return (
			<div className={paneClasses}>
				<PaneHeader
					colorIdentifier={this.props.colorIdentifier}
					listBy={this.state.listBy}
					onLookUp={this.handleLookUp}
					onViewSelected={this.handleListBy}
					orgName={orgName}
					spritemap={this.props.spritemap}
					totalAccounts={totalAccounts}
					totalSubOrg={totalSubOrg}
					totalUsers={totalUsers}
				/>

				<MembersList
					imagesPath={this.props.imagesPath}
					isLoading={this.state.isLoading}
					listBy={listBy}
					members={members}
					spritemap={this.props.spritemap}
				/>
			</div>
		);
	}
}

PropTypes.defaultProps = {
	apiURL: '',
	selectedId: 0,
};

MembersPane.propTypes = {
	apiURL: PropTypes.string,
	selectedId: PropTypes.number,
};

export default MembersPane;
