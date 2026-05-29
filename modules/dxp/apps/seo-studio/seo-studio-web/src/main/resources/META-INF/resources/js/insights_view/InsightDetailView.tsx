/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {getInsightType} from './services/InsightTypeService';
import {InsightType} from './types/InsightType';

export default function InsightDetailView({
	backURL,
	externalReferenceCode,
	screenName,
}: {
	backURL: string;
	externalReferenceCode: string;
	screenName: string;
}) {
	const [data, setData] = useState<InsightType>({});

	useEffect(() => {
		if (!externalReferenceCode) {
			return;
		}

		getInsightType(externalReferenceCode)
			.then((json) => setData(json))
			.catch(() => setData({}));
	}, [externalReferenceCode]);

	return (
		<div className="seo-studio-insight-detail">
			<ClayBreadcrumb
				items={[
					{href: backURL, label: screenName},
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
				<h3>{Liferay.Language.get('why-it-matters')}</h3>

				<p>{data.whyItMatters}</p>
			</section>

			<section className="seo-studio-insight-detail-section">
				<h3>{Liferay.Language.get('suggestion')}</h3>

				<p>{data.fixHint}</p>
			</section>
		</div>
	);
}
