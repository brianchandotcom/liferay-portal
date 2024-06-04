/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../../../../../../common/I18n';
import PopoverIconButton from '~/routes/customer-portal/components/PopoverIconButton';

const getInitialColumns = articleWhatIsMyInstanceSizingValueURL => [
	{
		accessor: 'start-end-date',
		align: 'center',
		bodyClass: 'border-0',
		expanded: true,
		header: {
			name: i18n.translate('start-end-date'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3'
		}
	},
	{
		accessor: 'quantity',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('purchased'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3'
		}
	},
	{
		accessor: 'instance-size',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: (
				<div className='align-items-center d-flex justify-content-center'>
					<p className='m-0'>{i18n.translate('instance-size')}</p>

					<PopoverIconButton
						popoverLink={{
							textLink: i18n.translate(
								'learn-more-about-instance-sizing'
							),
							url: articleWhatIsMyInstanceSizingValueURL
						}}
					/>
				</div>
			),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3'
		}
	},
	{
		accessor: 'subscription-term-status',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('status'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3'
		}
	}
];

const displayInstanceSizeMap = {
	Commerce: [
		'Backup',
		'Development',
		'Non-Production',
		'Production',
		'Unlimited Enterprise-Wide'
	],
	'Liferay Self-Hosted': [
		'Backup',
		'Development',
		'Flex',
		'Limited',
		'Non-Production',
		'OEM',
		'Production',
		'Unlimited Enterprise-Wide'
	],
	Portal: [
		'Backup',
		'Backup (Additional JVM)',
		'Development',
		'Early Access Program - Production',
		'Enterprise-Wide',
		'Limited',
		'Non-Production',
		'Non-Production (Additional JVM)',
		'Non-Production (Elastic)',
		'Non-Production (Monthly)',
		'OEM',
		'Portal Per User',
		'Production',
		'Production (Additional JVM)'
	]
};

export default function getColumns(
	title = '',
	articleWhatIsMyInstanceSizingValueURL
) {
	const columns = getInitialColumns(articleWhatIsMyInstanceSizingValueURL);

	const displayColumns = [...columns];

	const displayInstanceSizeForProduct = [
		'Commerce',
		'Liferay Self-Hosted',
		'Portal'
	].some(category => {
		if (title.startsWith(category)) {
			const productName = title.substring(category.length + 1);

			return displayInstanceSizeMap[category].includes(productName);
		}

		return false;
	});

	if (!displayInstanceSizeForProduct) {
		displayColumns.splice(2, 1);
	}

	return displayColumns;
}
