import * as API from 'shared/api';
import classNames from 'classnames';
import ClayButton from '@clayui/button';
import Loading, {Align} from 'shared/components/Loading';
import React, {useMemo} from 'react';
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

	const TriggerButton = useMemo(
		() =>
			React.forwardRef<HTMLButtonElement>((props, ref) => (
				<ClayButton
					{...props}
					className={classNames(className, 'rounded-lg')}
					disabled={loading}
					displayType='secondary'
					ref={ref}
					size='sm'
				>
					<Icon
						className='inline-item inline-item-before'
						symbol='filter'
					/>

					{filters[filterKey] ||
						sub(Liferay.Language.get('all-x'), [entityLabel])}

					{loading ? (
						<Loading align={Align.Right} />
					) : (
						<Icon
							className='inline-item inline-item-after'
							symbol='caret-bottom'
						/>
					)}
				</ClayButton>
			)),
		[className, entityLabel, filterKey, filters, loading]
	);

	return (
		<Picker
			as={TriggerButton}
			className='ml-3'
			onSelectionChange={(item: string) =>
				updateFilters({[filterKey]: item})
			}
			searchable
			triggerIcon='caret-bottom'
			value={filters[filterKey]}
		>
			{(data?.items ?? []).map((item: string) => (
				<Option key={item}>{item}</Option>
			))}
		</Picker>
	);
};

export default FieldValueFilter;
