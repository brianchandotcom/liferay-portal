/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Button from '@clayui/button';
import {DisplayType} from '@clayui/button/lib/Button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useRef} from 'react';

import LiferayFile from '../../../../../interfaces/liferayFile';
import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	displayType: DisplayType;
	onAccept: (liferayFile: LiferayFile) => void;
	outline?: boolean;
	small?: boolean;
}

const InputFile = ({
	description,
	displayType,
	field,
	label,
	meta,
	onAccept,
	outline,
	required,
	small,
	value,
}: PRMFormFieldProps & PRMFormFieldStateProps<LiferayFile> & IProps) => {
	const inputFileRef = useRef<HTMLInputElement>(null);

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const file = event.target.files?.[0];

		return file && onAccept(file);
	};

	return (
		<WrapperInput
			{...meta}
			description={description}
			label={label}
			required={required}
		>
			<div>
				<Button
					className={classNames('bg-neutral-0', {
						'border-danger': meta.touched && meta.error,
						'border-success': meta.touched && !meta.error,
					})}
					displayType={displayType}
					name={field.name}
					onBlur={field.onBlur}
					onClick={() => inputFileRef.current?.click()}
					outline={outline}
					small={small}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="upload" />
					</span>

					{value?.name || field.value?.name || 'Upload file'}
				</Button>
			</div>

			<ClayInput
				hidden
				name={field.name}
				onChange={handleChange}
				ref={inputFileRef}
				type="file"
				value=""
			/>
		</WrapperInput>
	);
};

export default InputFile;
