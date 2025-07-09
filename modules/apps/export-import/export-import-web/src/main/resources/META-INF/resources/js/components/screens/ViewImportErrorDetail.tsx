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
		entityExternalReferenceCode,
		entityId,
		entityScope,
		entitySite,
		entityType,
		errorId,
		errorMessage,
		errorStackTrace,
		errorType,
		dateCreated,
	} = errorDetail;

    return (
        <ClayLayout.ContainerFluid>
            <ClayLayout.Sheet className='m-4'>
                <ClayLayout.SheetHeader>
                    <h2 className="sheet-title">{entityType}</h2>
                    <div className="sheet-text">
                        {`${dateCreated} · ${creator.name}`}
                    </div>
                </ClayLayout.SheetHeader>
                <ClayLayout.SheetSection className='mb-2'>
                    <span className="sheet-subtitle text-secondary">
                        {Liferay.Language.get('error-details')}
                    </span>
                    <ClayLayout.ContentRow>
                        <ClayLayout.Col md={4}  className='pl-0'>
                            <DetailViewContentRow
								title={Liferay.Language.get('error-id')}
								body={errorId.toString()}
							/>
                        </ClayLayout.Col>
                        <ClayLayout.Col md={4}>
                            <DetailViewContentRow
								title={Liferay.Language.get('error-type')}
								body={errorType}
							/>
                        </ClayLayout.Col>
                        <ClayLayout.Col md={4}>
							<DetailViewContentRow
								title={Liferay.Language.get('entity-type')}								
								body={entityType}
							/>
                        </ClayLayout.Col>
                    </ClayLayout.ContentRow>
                    <ClayLayout.ContentRow>
                        <ClayLayout.Col md={12} className='pl-0'>
							<DetailViewContentRow
								title={Liferay.Language.get('error-message')}
								body={
									<textarea 
										className='lfr-textarea form-control' 
										rows={5} 
										readOnly 
										value={errorMessage} 
									/>
								}
							/>
                        </ClayLayout.Col>
                    </ClayLayout.ContentRow>
					<ClayLayout.ContentRow>
						<ClayLayout.Col md={3} className='pl-0'>
							<DetailViewContentRow
								body={
									<ClayButton displayType={'secondary'} onClick={() => openStackTraceModal({stackTraceMessage: errorStackTrace})}>
									{Liferay.Language.get('view-stack-trace')}
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
								title={Liferay.Language.get('entity-id')}
								body={entityId.toString()}
							/>
						</ClayLayout.Col>
						<ClayLayout.Col md={6}>
							<DetailViewContentRow
								title={Liferay.Language.get('external-reference-code')}
								body={entityExternalReferenceCode}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
					<ClayLayout.Row>
						<ClayLayout.Col md={6}>
							<DetailViewContentRow
								title={Liferay.Language.get('scope')}
								body={entityScope}
							/>
						</ClayLayout.Col>
						<ClayLayout.Col md={6}>
							<DetailViewContentRow 
								title={Liferay.Language.get('site')}
								body={entitySite}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
                </ClayLayout.SheetSection>
            </ClayLayout.Sheet>
            <ClayButton className='ml-4' displayType="secondary" onClick={() => window.history.back()}>
                {Liferay.Language.get('back')}
            </ClayButton>
    </ ClayLayout.ContainerFluid>
    );
}

interface DetailViewContentRowProps {
    title?: string;
    body: React.ReactNode;
}

const DetailViewContentRow = ({title, body}: DetailViewContentRowProps) => (
    <div className='sheet-text'>
        {title && <ClayLayout.ContentCol className='text-body'>
            <strong>{title}</strong> 
        </ClayLayout.ContentCol>}
        <ClayLayout.ContentCol>
            {body}
        </ClayLayout.ContentCol>
    </div>
);