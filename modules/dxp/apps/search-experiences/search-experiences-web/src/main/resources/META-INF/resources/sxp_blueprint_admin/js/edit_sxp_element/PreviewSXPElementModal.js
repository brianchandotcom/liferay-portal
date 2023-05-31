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

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import ErrorBoundary from '../shared/ErrorBoundary';
import JSONSXPElement from '../shared/JSONSXPElement';
import SXPElement from '../shared/sxp_element/index';
import {DEFAULT_HEADERS} from '../utils/fetch/fetch_data';
import getUIConfigurationValues from '../utils/sxp_element/get_ui_configuration_values';
import isCustomJSONSXPElement from '../utils/sxp_element/is_custom_json_sxp_element';

export default function PreviewSXPElementModal({
	isSXPElementJSONInvalid,
	onTitleAndDescriptionChange,
	sxpElementJSONObject,
}) {
	const [loading, setLoading] = useState(false);
	const [preview, setPreview] = useState({
		sxpElementJSONObject: {},
		uiConfigurationValues: {},
	});
	const [visible, setVisible] = useState(false);

	const {observer} = useModal({
		onClose: () => {
			setVisible(false);

			setPreview({
				sxpElementJSONObject: {},
				uiConfigurationValues: {},
			});
		},
	});

	const _fetchPreview = () => {
		setLoading(true);

		fetch('/o/search-experiences-rest/v1.0/sxp-elements/preview', {
			body: JSON.stringify({
				description_i18n: sxpElementJSONObject.description_i18n,
				elementDefinition: sxpElementJSONObject.elementDefinition,
				title_i18n: sxpElementJSONObject.title_i18n,
			}),
			headers: DEFAULT_HEADERS,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((jsonObject) => {
				const {description, title} = jsonObject;

				setPreview({
					sxpElementJSONObject: jsonObject,
					uiConfigurationValues: getUIConfigurationValues(jsonObject),
				});

				onTitleAndDescriptionChange({description, title});
			})
			.catch(() => {
				setPreview({
					sxpElementJSONObject,
					uiConfigurationValues: getUIConfigurationValues(
						sxpElementJSONObject
					),
				});
			})
			.finally(() => {
				setLoading(false);
			});
	};

	const _handleOpenModal = () => {
		setVisible(true);

		if (!isSXPElementJSONInvalid) {
			_fetchPreview();
		}
	};

	return (
		<>
			{visible && (
				<ClayModal
					className="sxp-preview-modal-root"
					observer={observer}
					size="lg"
				>
					<ClayModal.Header>
						{Liferay.Language.get('preview-configuration')}
					</ClayModal.Header>

					<ClayModal.Body>
						{!isSXPElementJSONInvalid ? (
							<div className="portlet-sxp-blueprint-admin">
								{loading ? (
									<div
										className="d-flex inline-item"
										style={{
											height: '400px',
										}}
									>
										<ClayLoadingIndicator
											displayType="secondary"
											size="sm"
										/>
									</div>
								) : (
									<ErrorBoundary>
										{isCustomJSONSXPElement(
											preview.uiConfigurationValues
										) ? (
											<JSONSXPElement
												collapseAll={false}
												readOnly={true}
												sxpElement={
													preview.sxpElementJSONObject
												}
												uiConfigurationValues={
													preview.uiConfigurationValues
												}
											/>
										) : (
											<SXPElement
												collapseAll={false}
												sxpElement={
													preview.sxpElementJSONObject
												}
												uiConfigurationValues={
													preview.uiConfigurationValues
												}
											/>
										)}
									</ErrorBoundary>
								)}
							</div>
						) : (
							<ClayEmptyState
								description={Liferay.Language.get(
									'json-may-be-incorrect-and-we-were-unable-to-load-a-preview-of-the-configuration'
								)}
								imgSrc="/o/admin-theme/images/states/empty_state.gif"
								title={Liferay.Language.get(
									'unable-to-load-preview'
								)}
							/>
						)}
					</ClayModal.Body>
				</ClayModal>
			)}

			<ClayButton
				borderless
				displayType="secondary"
				onClick={_handleOpenModal}
				small
			>
				{Liferay.Language.get('preview')}
			</ClayButton>
		</>
	);
}
