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

import React from 'react';
import SLAListCardContext from './SLAListCardContext';

class SLAConfirmDialog extends React.Component {
	cancel() {
		this.context.hideConfirmDialog();
	}

	removeItem() {
		const {itemToRemove} = this.props;

		this.context.removeItem(itemToRemove);
	}

	render() {
		return (
			<div className="modal show" role="dialog" tabIndex="-1">
				<div className="modal-dialog modal-lg">
					<div className="modal-content">
						<div className="modal-body">
							<p>
								{Liferay.Language.get(
									'deleting-slas-will-reflect-on-report-data'
								)}
							</p>
						</div>

						<div className="modal-footer">
							<div className="modal-item-last">
								<div className="btn-group">
									<div className="btn-group-item">
										<button
											className="btn btn-secondary"
											onClick={this.cancel.bind(this)}
											type="button"
										>
											{Liferay.Language.get('cancel')}
										</button>
									</div>

									<div className="btn-group-item">
										<button
											className="btn btn-secondary"
											id="remove_sla_button"
											onClick={this.removeItem.bind(this)}
											type="button"
										>
											{Liferay.Language.get('ok')}
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

SLAConfirmDialog.contextType = SLAListCardContext;
export default SLAConfirmDialog;
