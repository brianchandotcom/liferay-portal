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

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import React, {useState} from 'react';

const ERROR_OMIT_KEYS = [
	'className',
	'elementId',
	'localizedMessage',
	'msg',
	'throwable',
	'severity',
];

// Types from portal-search-tuning-blueprints-api/src/main/java/com/liferay/portal/search/tuning/blueprints/message/Severity.java

const SEVERITY_DISPLAY_TYPE = {
	ERROR: 'danger',
	INFO: 'info',
	WARN: 'warning',
};

const prettyPrint = (value) => {
	return JSON.stringify(value, null, 2);
};

function ErrorListItem({item, onFocusElement}) {
	const [collapse, setCollapse] = useState(true);

	const itemKeys = Object.keys(item);

	const _handleCollapse = () => {
		setCollapse(!collapse);
	};

	const _handleFocusElement = () => {
		onFocusElement(item.elementId);
	};

	return (
		<ClayAlert
			className="error-list-item"
			displayType={SEVERITY_DISPLAY_TYPE[item.severity] || 'danger'}
		>
			<span className="message" onClick={_handleCollapse}>
				<span className="title">
					{item.localizedMessage || Liferay.Language.get('error')}
				</span>

				<span className="description">{item.msg}</span>
			</span>

			{!!item.elementId && (
				<div className="scroll-button">
					<ClayButton alert onClick={_handleFocusElement} small>
						{Liferay.Language.get('view-element')}
					</ClayButton>
				</div>
			)}

			{itemKeys.length > ERROR_OMIT_KEYS.length && (
				<ClayButtonWithIcon
					borderless
					className="collapse-button text-danger"
					displayType="unstyled"
					onClick={_handleCollapse}
					small
					symbol={collapse ? 'angle-right' : 'angle-down'}
				/>
			)}

			{!collapse && itemKeys.length > ERROR_OMIT_KEYS.length && (
				<ClayAlert.Footer>
					{itemKeys.map(
						(property) =>
							!ERROR_OMIT_KEYS.includes(property) && (
								<ClayLayout.Row justify="start" key={property}>
									<ClayLayout.Col
										className="property"
										size={3}
									>
										{property}
									</ClayLayout.Col>

									<ClayLayout.Col size={9}>
										<code>
											{typeof item[property] === 'object'
												? prettyPrint(item[property])
												: item[property]}
										</code>
									</ClayLayout.Col>
								</ClayLayout.Row>
							)
					)}
				</ClayAlert.Footer>
			)}
		</ClayAlert>
	);
}

export default ErrorListItem;
