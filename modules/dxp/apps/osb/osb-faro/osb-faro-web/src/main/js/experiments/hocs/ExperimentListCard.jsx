import Card from 'shared/components/Card';
import getColumns from './columns';
import Icon from 'shared/components/Icon';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {Sizes} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {withBaseResults} from 'shared/hoc';

const ExperimentListCard = props => {
	const {experiments, timeZoneId, ...otherProps} = props;

	const withData = () => WrappedComponent => props =>
		<WrappedComponent {...props} {...otherProps} items={experiments} />;

	const TableWithData = withBaseResults(withData, {
		emptyDescription: (
			<>
				<span className='mr-1'>
					{sub(
						Liferay.Language.get(
							'create-a-new-test-from-liferay-dxp-by-clicking-on-the-x-icon-in-the-toolbar-when-viewing-a-page-in-DXP'
						),
						[<Icon key='ICON' symbol='ac-test' />],
						false
					)}
				</span>

				<a
					href={URLConstants.ExperimentDocumentationLink}
					key='DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get('learn-more-about-tests')}
				</a>
			</>
		),
		emptyIcon: {
			border: false,
			size: Sizes.XXXLarge,
			symbol: 'ac-satellite'
		},
		emptyTitle: Liferay.Language.get('there-are-no-tests-found'),
		getColumns: () => getColumns(timeZoneId),
		rowIdentifier: 'id',
		showDropdownRangeKey: false
	});

	return (
		<Card className='experiments-root' pageDisplay>
			<TableWithData
				{...props}
				entityLabel={Liferay.Language.get('tests')}
			/>
		</Card>
	);
};

export default ExperimentListCard;
