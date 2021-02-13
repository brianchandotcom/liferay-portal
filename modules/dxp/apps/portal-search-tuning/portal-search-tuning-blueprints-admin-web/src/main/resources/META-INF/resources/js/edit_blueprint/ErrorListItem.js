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

const ERROR_OMIT_KEYS = ['elementId', 'msg', 'severity', 'localizationKey'];

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

	const _handleClick = () => {
		setCollapse(!collapse);
	};

	return (
		<ClayAlert
			className="error-list-item"
			displayType={SEVERITY_DISPLAY_TYPE[item.severity] || 'danger'}
		>
			<span className="message" onClick={_handleClick}>
				{/* TODO: Get translated `localizationKey` */}

				<span className="title">{Liferay.Language.get('error')}</span>
				<span className="description">{item.msg}</span>
			</span>

			{item.elementId > -1 && (
				<div className="scroll-button">
					<ClayButton
						alert
						onClick={() => onFocusElement(item.elementId)}
						small
					>
						{Liferay.Language.get('view-element')}
					</ClayButton>
				</div>
			)}

			{itemKeys.length > ERROR_OMIT_KEYS.length && (
				<ClayButtonWithIcon
					borderless
					className="collapse-button text-danger"
					displayType="unstyled"
					onClick={_handleClick}
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
