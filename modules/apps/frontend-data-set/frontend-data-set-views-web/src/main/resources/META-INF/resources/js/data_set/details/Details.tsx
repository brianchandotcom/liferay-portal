/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {IDataSet} from '../../DataSets';
import {FDSViewType} from '../../FDSViews';
import RequiredMark from '../../components/RequiredMark';
import {API_URL, OBJECT_RELATIONSHIP} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import {IDataSetSectionProps} from '../DataSet';

const Details = ({
	backURL,
	dataSet,
	namespace,
	onDataSetUpdate,
}: IDataSetSectionProps) => {
	const [labelValidationError, setLabelValidationError] = useState(false);

	const descriptionRef = useRef<HTMLInputElement>(null);
	const labelRef = useRef<HTMLInputElement>(null);

	const updateFDSView = async () => {
		const body = {
			description: descriptionRef.current?.value,
			label: labelRef.current?.value,
		};

		const response = await fetch(
			`${API_URL.DATA_SETS}/by-external-reference-code/${dataSet.externalReferenceCode}`,
			{
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		if (responseJSON?.id) {
			openDefaultSuccessToast();

			const controlMenuHeaderTitles = document.getElementsByClassName(
				'control-menu-level-1-heading'
			);

			if (controlMenuHeaderTitles.length === 1) {
				controlMenuHeaderTitles[0].innerHTML = Liferay.Util.escapeHTML(
					labelRef.current?.value ?? ''
				);
			}
			onDataSetUpdate(responseJSON);
		}
		else {
			openDefaultFailureToast();
		}
	};

	const {restApplication, restEndpoint, restSchema} = Liferay.FeatureFlags[
		'LPD-15729'
	]
		? ((dataSet as unknown) as IDataSet)
		: ((dataSet as unknown) as FDSViewType)[
				OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
		  ];

	return (
		<ClayLayout.Sheet className="mt-3" size="lg">
			<ClayLayout.SheetHeader>
				<h2 className="sheet-title">
					{Liferay.Language.get('details')}
				</h2>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<ClayForm.Group
					className={classNames({
						'has-error': labelValidationError,
					})}
				>
					<label htmlFor={`${namespace}dataSetLabelInput`}>
						{Liferay.Language.get('name')}

						<RequiredMark />
					</label>

					<ClayInput
						defaultValue={dataSet.label}
						id={`${namespace}dataSetLabelInput`}
						onBlur={() =>
							setLabelValidationError(!labelRef.current?.value)
						}
						ref={labelRef}
						type="text"
					/>

					{labelValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get('this-field-is-required')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}dataSetDesctiptionInput`}>
						{Liferay.Language.get('description')}
					</label>

					<ClayInput
						defaultValue={dataSet.description}
						id={`${namespace}dataSetDesctiptionInput`}
						ref={descriptionRef}
						type="text"
					/>
				</ClayForm.Group>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection className="mb-4">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('rest-information')}
				</h3>

				<ClayList className="flex-row flex-wrap">
					<ClayList.Item className="border-0 col-12 col-sm-6" flex>
						<ClayList.ItemField className="justify-content-center">
							<ClayIcon symbol="api-web" />
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{Liferay.Language.get('application')}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{restApplication}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>

					<ClayList.Item className="border-0 col-12 col-sm-6" flex>
						<ClayList.ItemField className="justify-content-center">
							<ClayIcon symbol="diagram" />
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayList.ItemTitle>
								{Liferay.Language.get('schema')}
							</ClayList.ItemTitle>

							<ClayList.ItemText>{restSchema}</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>

					<ClayList.Item className="border-0 col-12" flex>
						<ClayList.ItemField className="justify-content-center">
							<ClayIcon symbol="nodes" />
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayList.ItemTitle>
								{Liferay.Language.get('endpoint')}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{restEndpoint}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>
				</ClayList>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetFooter>
				<ClayButton.Group spaced>
					<ClayButton onClick={updateFDSView}>
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => navigate(backURL)}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</ClayLayout.SheetFooter>
		</ClayLayout.Sheet>
	);
};

export default Details;
