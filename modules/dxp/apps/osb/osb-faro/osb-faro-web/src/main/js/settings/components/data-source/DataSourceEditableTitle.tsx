import ClayLabel from '@clayui/label';
import InputWithEditToggle from 'shared/components/InputWithEditToggle';
import React, {useCallback, useRef} from 'react';
import {sequence} from 'shared/util/promise';
import {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/util/validators';
import {validateUniqueName} from 'shared/util/data-sources';

const DataSourceEditableTitle = ({
	dataSource,
	displayType,
	editable,
	groupId,
	label,
	onUpdateName
}) => {
	const cachedNameValues = useRef(new Map());

	const handleUpdateName = useCallback(onUpdateName, [
		groupId,
		dataSource.id
	]);

	const handleValidate = useCallback(
		value => {
			let error = null;

			if (value !== dataSource.name) {
				if (cachedNameValues.current.has(value)) {
					error = cachedNameValues.current.get(value);
				} else {
					error = validateUniqueName({groupId, value});

					cachedNameValues.current.set(value, error);
				}
			}

			return toPromise(error);
		},
		[dataSource.name, groupId]
	);

	return (
		<div className='mb-5'>
			<ClayLabel className='mb-2' displayType={displayType}>
				{label}
			</ClayLabel>

			<InputWithEditToggle
				editable={editable}
				inputWidth={30}
				name='dataSourceName'
				onSubmit={name => toPromise(handleUpdateName(name))}
				required
				validate={sequence([
					validateRequired,
					validateMaxLength(75),
					handleValidate
				])}
				value={dataSource.name}
			/>
		</div>
	);
};

export {DataSourceEditableTitle};
