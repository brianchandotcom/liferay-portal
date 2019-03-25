import React from 'react';

export default class SLAConfirmDialog extends React.Component {
	render() {
		const {item: itemRemoved} = this.props;

		return (
			<div className="modal" role="dialog" tabIndex="-1">
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
										<button className="btn btn-secondary" type="button">
											{Liferay.Language.get('cancel')}
										</button>
									</div>

									<div className="btn-group-item">
										<button className="btn btn-secondary" type="button">
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