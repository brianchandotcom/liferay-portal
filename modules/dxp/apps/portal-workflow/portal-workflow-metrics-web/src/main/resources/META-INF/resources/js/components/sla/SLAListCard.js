import { AppContext, AppStatus } from '../AppContext';
import { openErrorToast, openSuccessToast } from '../../shared/util/toast';
import autobind from 'autobind-decorator';
import { ChildLink } from '../../shared/components/router/routerWrapper';
import DisplayResult from '../../shared/components/pagination/DisplayResult';
import EmptyContent from '../../shared/components/EmptyContent';
import Icon from '../../shared/components/Icon';
import PageSizeEntries from '../../shared/components/pagination/PageSizeEntries';
import Pagination from '../../shared/components/pagination/Pagination';
import React from 'react';
import SLAConfirmDialog from './SLAConfirmDialog';
import SLAListCardContext from './SLAListCardContext';
import SLAListTable from './SLAListTable';

const REQUEST_ORIGIN_TYPE_FETCH = 'REQUEST_ORIGIN_TYPE_FETCH';
const REQUEST_ORIGIN_TYPE_SEARCH = 'REQUEST_ORIGIN_TYPE_SEARCH';

class SLAListCard extends React.Component {
	constructor(props) {
		super(props);

		const { page = 1, pageSize = 20, processId } = this.props;

		this.state = {
			items: [],
			itemToRemove: null,
			page,
			pageSize,
			processId,
			requestOriginType: null,
			showConfirmDialog: false,
			showSLAsUpdatingAlert: false,
			totalCount: 0
		};

		this.slaContextState = {
			hideConfirmDialog: () => this.setConfirmDialogVisibility(null, false),
			removeItem: this.removeItem.bind(this),
			showConfirmDialog: (id, callback) =>
				this.setConfirmDialogVisibility(id, true, callback)
		};
	}

	componentDidMount() {
		this.loadData();
	}

	componentWillMount() {
		this.context.setTitle(Liferay.Language.get('slas'));
	}

	componentWillUpdate() {
		this.showStatusMessage();
	}

	loadData() {
		const { page, pageSize, processId } = this.state;

		this.requestData({
			page,
			pageSize,
			processId
		}).then(({ items, totalCount }) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	removeItem(id) {
		const { client } = this.context;

		client
			.delete(`/slas/${id}`)
			.then(() => {
				this.loadData();
				openSuccessToast(Liferay.Language.get('sla-deleted'));
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
		const { status } = this.context;

		if (status === AppStatus.slaUpdated || status === AppStatus.slaSaved) {
			if (status === AppStatus.slaUpdated) {
				openSuccessToast(Liferay.Language.get('sla-updated'));
			}
			else {
				openSuccessToast(Liferay.Language.get('sla-saved'));
			}

			this.state.showSLAsUpdatingAlert = true;
			this.context.setStatus(null);
		}
	}

	/**
	 * @param {Object} configuration
	 * @param {number} configuration.page
	 * @param {number} configuration.pageSize
	 * @param {number} configuration.processId
	 * @param {string} configuration.title
	 */
	requestData({ page, pageSize, processId, title }) {
		const { client } = this.context;

		this.state.requestOriginType =
			typeof title === 'string'
				? REQUEST_ORIGIN_TYPE_SEARCH
				: REQUEST_ORIGIN_TYPE_FETCH;

		return client
			.get(`/processes/${processId}/slas?page=${page}&pageSize=${pageSize}`)
			.then(({ data }) => data)
			.catch(openErrorToast);
	}

	@autobind
	setPage({ page, pageSize }) {
		const { processId } = this.props;

		return this.requestData({ page, pageSize, processId }).then(
			({ items, totalCount }) => this.setState({ items, page, totalCount })
		);
	}

	@autobind
	setPageSize(pageSize) {
		const { processId } = this.props;
		const page = 1;

		return this.requestData({ page, pageSize, processId }).then(
			({ items, totalCount }) =>
				this.setState({ items, page, pageSize, totalCount })
		);
	}

	render() {
		const {
			itemToRemove,
			items,
			page,
			pageSize,
			requestOriginType,
			showConfirmDialog,
			showSLAsUpdatingAlert,
			totalCount
		} = this.state;
		const pageSizes = [5, 10, 20, 30, 50, 75];
		const { processId } = this.props;

		const emptySearchRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_SEARCH && totalCount === 0 ? (
				<EmptyContent
					message={Liferay.Language.get('no-results-were-found')}
					type="not-found"
				/>
			) : (
				secondaryRender
			);

		const emptyContentRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0 ? (
				<EmptyContent
					message={Liferay.Language.get(
						'once-there-are-active-processes-metrics-will-appear-here'
					)}
					title={Liferay.Language.get('no-current-slas')}
				/>
			) : (
				secondaryRender
			);

		const listRender = secondaryRender =>
			totalCount > 0 ? (
				<div>
					{showConfirmDialog && (
						<SLAConfirmDialog itemToRemove={itemToRemove} />
					)}

					<SLAListTable sla={items} />

					{totalCount > pageSizes[0] && (
						<div className="pagination-bar">
							<PageSizeEntries
								onSelectPageSize={this.setPageSize}
								pageSizeEntries={pageSizes}
								selectedPageSize={pageSize}
							/>

							<DisplayResult
								page={page}
								pageCount={items.length}
								pageSize={pageSize}
								totalCount={totalCount}
							/>

							<Pagination
								onSelectPage={this.setPage}
								page={page}
								pageSize={pageSize}
								totalCount={totalCount}
							/>
						</div>
					)}
				</div>
			) : (
				secondaryRender
			);

		const loadingRender = () => (
			<span aria-hidden="true" className="loading-animation" />
		);

		return (
			<SLAListCardContext.Provider value={this.slaContextState}>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<ul className="navbar-nav autofit-row">
							<li className="nav-item autofit-col-expand autofit-float-end">
								<ChildLink
									className="btn btn-primary nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
									to={`/sla/new/${processId}`}
								>
									<Icon iconName="plus" />
								</ChildLink>
							</li>
						</ul>
					</div>
				</nav>

				<div className="container-fluid-1280">
					{showSLAsUpdatingAlert && (
						<div className="alert alert-dismissible alert-info" role="alert">
							<span className="alert-indicator">
								<Icon iconName="reload" />
							</span>

							<strong className="lead">{Liferay.Language.get('info')}</strong>

							<span>
								{Liferay.Language.get('one-or-more-slas-are-being-updated')}{' '}
								{Liferay.Language.get(
									'there-may-be-a-delay-before-sla-changes-are-fully-propagated'
								)}
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

					{emptySearchRender(emptyContentRender(listRender(loadingRender())))}
				</div>
			</SLAListCardContext.Provider>
		);
	}
}

SLAListCard.contextType = AppContext;
export default SLAListCard;