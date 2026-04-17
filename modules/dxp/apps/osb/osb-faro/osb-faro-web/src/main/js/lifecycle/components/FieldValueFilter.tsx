import * as API from 'shared/api';
import classNames from 'classnames';
import ClayButton from '@clayui/button';
import Loading from 'shared/components/Loading';
import React from 'react';
import {Icon, Option, Picker} from '@clayui/core';
import {sub} from 'shared/util/lang';
import {useLifecycle} from '../context/LifecycleContext';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

interface IProps {
	className?: string;
	entityLabel: string;
	fieldMappingFieldName: string;
	filterKey: 'countryFilter' | 'industryFilter';
}

const FieldValueFilter = ({
	className,
	entityLabel,
	fieldMappingFieldName,
	filterKey
}: IProps) => {
	const {filters, updateFilters} = useLifecycle();

	const {channelId, groupId} = useParams();

	const {data, loading} = useRequest({
		dataSourceFn: API.accounts.fetchFieldValues,
		variables: {
			channelId,
			fieldMappingFieldName,
			groupId,
			query: ''
		}
	});

	if (loading) {
		return <Loading />;
	}

	return (
		<Picker
			as={React.forwardRef((props, ref) => (
				<ClayButton
					{...props}
					className={classNames(className, 'rounded-lg')}
					displayType='secondary'
					ref={ref}
					searchable
					size='sm'
				>
					<Icon
						className='inline-item inline-item-before'
						symbol='filter'
					/>

					{filters[filterKey] ||
						sub(Liferay.Language.get('all-x'), [entityLabel])}

					<Icon
						className='inline-item inline-item-after'
						symbol='caret-bottom'
					/>
				</ClayButton>
			))}
			className='ml-3'
			onSelectionChange={(item: string) =>
				updateFilters({[filterKey]: item})
			}
			triggerIcon='caret-bottom'
			value={filters[filterKey]}
		>
			{data?.items?.map((item: string) => (
				<Option key={item}>{item}</Option>
			))}
		</Picker>
	);
};

export default FieldValueFilter;
