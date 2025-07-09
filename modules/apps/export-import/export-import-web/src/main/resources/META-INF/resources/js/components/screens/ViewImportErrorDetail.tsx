/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {openModal} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import formatDate from '../../utils/formatDate';
import {DetailViewContentRow} from './DetailViewContentRow';

interface ErrorDetail {
	creator: {
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	entityExternalReferenceCode: string;
	entityId: number;
	entityScope: string;
	entitySite: string;
	entityType: string;
	errorId: number;
	errorMessage: string;
	errorStackTrace: string;
	errorType: string;
	externalReferenceCode: string;
}

export function ViewImportErrorDetail() {
	const [errorDetail, setErrorDetail] = useState<ErrorDetail>({
		creator: {
			name: '',
		},
		dateCreated: '',
		dateModified: '',
		entityExternalReferenceCode: '',
		entityId: 0,
		entityScope: '',
		entitySite: '',
		entityType: '',
		errorId: 0,
		errorMessage: '',
		errorStackTrace: '',
		errorType: '',
		externalReferenceCode: '',
	});

	useEffect(() => {
		fetch('/group/__mocks__/get-import-error-detail').then((response) => {
			response.json().then((data: ErrorDetail) => {
				setErrorDetail({
					...data,
					dateCreated: formatDate(data.dateCreated),
				});
			});
		});
	}, []);

	function openStackTraceModal({
		stackTraceMessage,
	}: {
		stackTraceMessage: string;
	}) {
		openModal({
			bodyHTML: `
				<div class="bg-dark border border-light p-4 rounded">
					<p class="text-white">
                        ${stackTraceMessage}
					</p>
				</div>
			`,
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('close'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();
					},
				},
			],
			size: 'full-screen',
			title: Liferay.Language.get('stack-trace'),
		});
	}

	const {
		creator,
		dateCreated,
		entityExternalReferenceCode,
		entityId,
		entityScope,
		entitySite,
		entityType,
		errorId,
		errorMessage,
		errorStackTrace,
		errorType,
	} = errorDetail;

	return (
		<ClayLayout.ContainerFluid>
			<ClayLayout.Sheet className="m-4">
				<ClayLayout.SheetHeader>
					<h2 className="sheet-title">{entityType}</h2>

					<div className="sheet-text">
						{`${dateCreated} · ${creator.name}`}
					</div>
				</ClayLayout.SheetHeader>

				<ClayLayout.SheetSection className="mb-2">
					<span className="sheet-subtitle text-secondary">
						{Liferay.Language.get('error-details')}
					</span>

					<ClayLayout.ContentRow>
						<ClayLayout.Col className="pl-0" md={4}>
							<DetailViewContentRow
								body={errorId.toString()}
								title={Liferay.Language.get('error-id')}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col md={4}>
							<DetailViewContentRow
								body={errorType}
								title={Liferay.Language.get('error-type')}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col md={4}>
							<DetailViewContentRow
								body={entityType}
								title={Liferay.Language.get('entity-type')}
							/>
						</ClayLayout.Col>
					</ClayLayout.ContentRow>

					<ClayLayout.ContentRow>
						<ClayLayout.Col className="pl-0" md={12}>
							<DetailViewContentRow
								body={
									<textarea
										className="form-control lfr-textarea"
										readOnly
										rows={5}
										value={errorMessage}
									/>
								}
								title={Liferay.Language.get('error-message')}
							/>
						</ClayLayout.Col>
					</ClayLayout.ContentRow>

					<ClayLayout.ContentRow>
						<ClayLayout.Col className="pl-0" md={3}>
							<DetailViewContentRow
								body={
									<ClayButton
										displayType="secondary"
										onClick={() =>
											openStackTraceModal({
												stackTraceMessage:
													errorStackTrace,
											})
										}
									>
										{Liferay.Language.get(
											'view-stack-trace'
										)}
									</ClayButton>
								}
							/>
						</ClayLayout.Col>
					</ClayLayout.ContentRow>
				</ClayLayout.SheetSection>

				<ClayLayout.SheetSection>
					<span className="sheet-subtitle text-secondary">
						{Liferay.Language.get('failed-event')}
					</span>

					<ClayLayout.Row>
						<ClayLayout.Col md={6}>
							<DetailViewContentRow
								body={entityId.toString()}
								title={Liferay.Language.get('entity-id')}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col md={6}>
							<DetailViewContentRow
								body={entityExternalReferenceCode}
								title={Liferay.Language.get(
									'external-reference-code'
								)}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<ClayLayout.Row>
						<ClayLayout.Col md={6}>
							<DetailViewContentRow
								body={entityScope}
								title={Liferay.Language.get('scope')}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col md={6}>
							<DetailViewContentRow
								body={entitySite}
								title={Liferay.Language.get('site')}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.SheetSection>
			</ClayLayout.Sheet>

			<ClayButton
				className="ml-4"
				displayType="secondary"
				onClick={() => window.history.back()}
			>
				{Liferay.Language.get('back')}
			</ClayButton>
		</ClayLayout.ContainerFluid>
	);
}
