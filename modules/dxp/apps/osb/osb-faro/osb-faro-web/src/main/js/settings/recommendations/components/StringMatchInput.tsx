import BaseSelect from 'shared/components/BaseSelect';
import getCN from 'classnames';
import MetadataTag from './MetadataTag';
import Promise from 'metal-promise';
import React, {useEffect, useRef} from 'react';
import {BACKSPACE, ENTER} from 'shared/util/key-constants';
import {METADATA_TAGS} from '../utils/utils';

interface IStringMatchInputProps
	extends React.HTMLAttributes<HTMLInputElement> {
	focusOnInit?: boolean;
	metadata: string;
	onEnterClick: () => void;
	onMetadataChange: (value: string) => void;
	onStringMatchChange: (value: string) => void;
	stringMatch: string;
}

const getMetadataTag = (value: string): string[] =>
	METADATA_TAGS.filter(tag =>
		tag.toLowerCase().includes(value.toLowerCase())
	);

const StringMatchInput: React.FC<IStringMatchInputProps> = ({
	className,
	focusOnInit = true,
	metadata = '',
	onEnterClick,
	onMetadataChange,
	onStringMatchChange,
	stringMatch = ''
}) => {
	const _inputRef = useRef<HTMLInputElement>();

	const metadataResults: boolean = !!getMetadataTag(stringMatch).length;

	useEffect(() => {
		if (metadata) {
			onStringMatchChange('');
		}
	}, [metadata]);

	useEffect(() => {
		if (!!metadata || !metadataResults) {
			_inputRef.current.focus();
		}
	}, [metadata, stringMatch]);

	return (
		<div className={getCN('string-match-input-root', className)}>
			{(!!metadata || !metadataResults) && (
				<div className='form-control form-control-tag-group'>
					{!!metadata && <MetadataTag value={metadata} />}

					<input
						className='form-control-inset'
						onChange={event => {
							const {value} = event.target;

							onStringMatchChange(value);
						}}
						onKeyDown={event => {
							const {keyCode, target} = event;

							const {value} = target as HTMLInputElement;

							if (
								keyCode === BACKSPACE &&
								!stringMatch &&
								!value
							) {
								onMetadataChange('');
							} else if (keyCode === ENTER) {
								onEnterClick();
							}
						}}
						ref={_inputRef}
						value={stringMatch}
					/>
				</div>
			)}

			{!metadata && metadataResults && (
				<BaseSelect
					className='form-control-inset'
					dataSourceFn={query =>
						Promise.resolve(getMetadataTag(query as string))
					}
					focusOnInit={focusOnInit}
					inputValue={stringMatch}
					itemRenderer={value => <MetadataTag value={value} />}
					menuTitle={Liferay.Language.get('available-metadata')}
					onInputValueChange={onStringMatchChange}
					onSelect={onMetadataChange}
				/>
			)}
		</div>
	);
};

export default StringMatchInput;
