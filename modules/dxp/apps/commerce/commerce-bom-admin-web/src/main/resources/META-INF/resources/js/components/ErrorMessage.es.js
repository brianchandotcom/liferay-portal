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

function ErrorMessage(props) {
	return (
		<div className="alert-container container">
			<div className="alert-notifications alert-notifications-absolute">
				<div
					className="alert alert-danger alert-dismissible"
					role="alert"
				>
					{Liferay.Language.get('unexpected-error')}
					{props.closeIcon && (
						<button
							aria-label="Close"
							className="close"
							data-dismiss="alert"
							onClick={props.onClose}
							type="button"
						>
							{props.closeIcon}
						</button>
					)}
				</div>
			</div>
		</div>
	);
}

export default ErrorMessage;
