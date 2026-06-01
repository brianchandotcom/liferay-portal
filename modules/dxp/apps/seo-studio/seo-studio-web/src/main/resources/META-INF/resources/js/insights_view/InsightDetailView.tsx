/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {getInsightType} from './services/InsightTypeService';
import {InsightType} from './types/InsightType';

type BreadcrumbItem = {
	href?: string;
	label: string;
};

export default function InsightDetailView({
	apiURL,
	breadcrumbItems,
	externalReferenceCode,
	fdsId,
	views,
}: {
	apiURL: string;
	breadcrumbItems: BreadcrumbItem[];
	externalReferenceCode: string;
	fdsId: string;
	views: any[];
}) {
	const [data, setData] = useState<InsightType>({});

	useEffect(() => {
		if (!externalReferenceCode) {
			return;
		}

		getInsightType(externalReferenceCode)
			.then((json) => setData(json))
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'unable-to-load-insight-details'
					),
					type: 'danger',
				});

				window.location.assign(breadcrumbItems[0]?.href ?? '');
			});
	}, [breadcrumbItems, externalReferenceCode]);

	return (
		<div className="seo-studio-insight-detail">
			<ClayBreadcrumb
				items={[
					...breadcrumbItems,
					{active: true, label: data.name ?? ''},
				]}
			/>

			<h2 className="seo-studio-insight-detail-title">
				{sub(
					Liferay.Language.get('x-from-x-pages'),
					data.name ?? '',
					String(data.affectedPagesCount ?? 0)
				)}
			</h2>

			<section className="seo-studio-insight-detail-section">
				<h3>{Liferay.Language.get('description')}</h3>

				<p>{data.description}</p>
			</section>

			<section className="seo-studio-insight-detail-section">
				<h3>{Liferay.Language.get('suggestion')}</h3>

				<p>{data.fixHint}</p>
			</section>

			<section className="seo-studio-insight-detail-section">
				<h3>
					{`${Liferay.Language.get('affected-pages')} (${
						data.affectedPagesCount ?? 0
					})`}
				</h3>

				<FrontendDataSet
					apiURL={apiURL}
					appURL={`${Liferay.ThemeDisplay.getPortalURL()}/o/frontend-data-set-taglib/app`}
					id={fdsId}
					pagination={{initialDelta: 10}}
					showManagementBar={false}
					showPagination
					showSearch={false}
					views={views}
				/>
			</section>
		</div>
	);
}
