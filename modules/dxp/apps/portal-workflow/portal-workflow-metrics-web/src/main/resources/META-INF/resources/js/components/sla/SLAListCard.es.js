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

import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import Icon from '../../shared/components/Icon.es';
import ListView from '../../shared/components/list/ListView.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import {ChildLink} from '../../shared/components/router/routerWrapper.es';
import {openErrorToast, openSuccessToast} from '../../shared/util/toast.es';
import {AppContext, AppStatus} from '../AppContext.es';
import {REQUEST_ORIGIN_TYPE_FETCH} from './Constants.es';
import SLAConfirmDialog from './SLAConfirmDialog.es';
import SLAListCardContext from './SLAListCardContext.es';
import SLAListTable from './SLAListTable.es';

class SLAListCard extends React.Component {
	constructor(props) {
		super(props);

		this.requestOriginType = null;
		this.state = {
			blockedSLACount: 0,
			itemToRemove: null,
			items: [],
			showConfirmDialog: false,
			showSLAsUpdatingAlert: false,
			totalCount: 0
		};

		this.slaContextState = {
			hideConfirmDialog: () =>
				this.setConfirmDialogVisibility(null, false),
			removeItem: this.removeItem.bind(this),
			showConfirmDialog: (id, callback) =>
				this.setConfirmDialogVisibility(id, true, callback)
		};
	}

	componentDidMount() {
		this.loadBlockedSLA();
	}

	componentWillMount() {
		this.context.setTitle(Liferay.Language.get('slas'));
		this.showStatusMessage();
	}

	componentWillReceiveProps(nextProps) {
		this.loadData(nextProps);
	}

	loadData(props = this.props) {
		const {page, pageSize, processId} = props;

		this.requestData({
			page,
			pageSize,
			processId
		}).then(({items, totalCount}) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	loadBlockedSLA() {
		const {client} = this.context;
		const {processId} = this.props;

		client
			.get(`/processes/${processId}/slas?page=1&pageSize=1&status=2`)
			.then(({data: {totalCount: blockedSLACount}}) => {
				this.setState({
					blockedSLACount
				});
			});
	}

	removeItem(id) {
		const {client} = this.context;

		client
			.delete(`/slas/${id}`)
			.then(() => {
				this.loadData();
				openSuccessToast(Liferay.Language.get('sla-was-deleted'));
			})
			.catch(openErrorToast);

		this.setState({
			itemToRemove: null,
			showConfirmDialog: false
		});
	}

	setConfirmDialogVisibility(id, visible, callback) {
		this.setState(
			{
				itemToRemove: id,
				showConfirmDialog: visible
			},
			callback
		);
	}

	showStatusMessage() {
		const {status} = this.context;

		if (status === AppStatus.slaUpdated || status === AppStatus.slaSaved) {
			if (status === AppStatus.slaUpdated) {
				openSuccessToast(Liferay.Language.get('sla-was-updated'));
			}
			else {
				openSuccessToast(Liferay.Language.get('sla-was-saved'));
			}

			// eslint-disable-next-line react/no-direct-mutation-state
			this.state.showSLAsUpdatingAlert = true;
			this.context.setStatus(null);
		}
	}

	/**
	 * @param {Object} configuration
	 * @param {number} configuration.page
	 * @param {number} configuration.pageSize
	 * @param {number} configuration.processId
	 */
	requestData({page, pageSize, processId}) {
		const {client} = this.context;

		return client
			.get(
				`/processes/${processId}/slas?page=${page}&pageSize=${pageSize}`
			)
			.then(({data}) => {
				this.requestOriginType = REQUEST_ORIGIN_TYPE_FETCH;

				return data;
			})
			.catch(openErrorToast);
	}

	render() {
		const {requestOriginType} = this;
		const {
			blockedSLACount = 0,
			items = [],
			itemToRemove,
			showConfirmDialog,
			showSLAsUpdatingAlert,
			totalCount
		} = this.state;
		const {page, pageSize, processId} = this.props;
		const emptyMessageText = Liferay.Language.get(
			'sla-allows-to-define-and-measure-process-performance'
		);
		const emptyTitleText = Liferay.Language.get('no-slas-yet');

		const fetching =
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0;
		const loading = !requestOriginType && totalCount === 0;

		return (
			<SLAListCardContext.Provider value={this.slaContextState}>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<ul className="autofit-row navbar-nav">
							<li className="autofit-col-expand autofit-float-end nav-item">
								<ClayTooltipProvider>
									<span>
										<span
											className="workflow-tooltip"
											data-tooltip-align={'bottom'}
											title={Liferay.Language.get(
												'new-sla'
											)}
										>
											<ChildLink
												className="btn btn-primary nav-btn nav-btn-monospaced"
												to={`/sla/new/${processId}`}
											>
												<Icon iconName="plus" />
											</ChildLink>
										</span>
									</span>
								</ClayTooltipProvider>
							</li>
						</ul>
					</div>
				</nav>

				{showConfirmDialog && (
					<SLAConfirmDialog itemToRemove={itemToRemove} />
				)}

				<div className="container-fluid-1280">
					{!!blockedSLACount && (
						<div
							className="alert alert-danger alert-dismissible"
							role="alert"
						>
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">
								{Liferay.Language.get('error')}
							</strong>

							{Liferay.Language.get(
								'fix-blocked-slas-to-resume-accurate-reporting'
							)}

							<button
								aria-label="Close"
								className="close"
								data-dismiss="alert"
								type="button"
							>
								<Icon iconName="times" />
							</button>
						</div>
					)}

					{showSLAsUpdatingAlert && (
						<div
							className="alert alert-dismissible alert-info"
							role="alert"
						>
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">
								{Liferay.Language.get('info')}
							</strong>

							<span>
								{`${Liferay.Language.get(
									'one-or-more-slas-are-being-updated'
								)} ${Liferay.Language.get(
									'there-may-be-a-delay-before-sla-changes-are-fully-propagated'
								)}`}
							</span>

							<button
								aria-label="Close"
								className="close"
								data-dismiss="alert"
								type="button"
							>
								<Icon iconName="times" />
							</button>
						</div>
					)}

					<ListView
						emptyMessageText={emptyMessageText}
						emptyTitleText={emptyTitleText}
						fetching={fetching}
						loading={loading}
					>
						<SLAListTable items={items} />

						<PaginationBar
							page={page}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</ListView>
				</div>
			</SLAListCardContext.Provider>
		);
	}
}

SLAListCard.contextType = AppContext;
export default SLAListCard;
