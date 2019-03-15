import Link from '../../shared/components/router/Link';
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
										<Link
											className="btn btn-secondary"
											text={Liferay.Language.get('cancel')}
											to="sla-list"
										/>
									</div>

									<div className="btn-group-item">
										<Link
											className="btn btn-secondary"
											query={{itemRemoved}}
											text={Liferay.Language.get('ok')}
											to="sla-list"
										/>
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