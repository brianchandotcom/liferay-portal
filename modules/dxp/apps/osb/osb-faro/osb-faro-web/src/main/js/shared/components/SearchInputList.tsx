import BaseSelect from './BaseSelect';
import getCN from 'classnames';
import Label from 'shared/components/Label';
import React, {useEffect, useRef, useState} from 'react';

interface ISearchInputListProps extends React.HTMLAttributes<HTMLElement> {
	clearOnAdd: boolean;
	containerClass?: string;
	dataSourceFn: (inputValue: string) => Promise<any>;
	disabled: boolean;
	itemRenderer?: () => React.ReactNode;
	items: any[];
	onItemsChange: (items: any[]) => void;
	placeholder?: string;
}

const SearchInputList: React.FC<ISearchInputListProps> = ({
	className,
	clearOnAdd = false,
	containerClass,
	dataSourceFn,
	disabled,
	itemRenderer,
	items,
	onItemsChange,
	placeholder = ''
}) => {
	const [focus, setFocus] = useState<boolean>(false);
	const [query, setQuery] = useState<string>('');

	const _baseSelectRef = useRef<any>();

	useEffect(() => {
		if (_baseSelectRef && !!items.length) {
			_baseSelectRef.current.focus();
		}
	}, [items]);

	const handleQueryChange = (value: string): void => {
		setQuery(value);
	};

	const handleAddItem = (item: any): void => {
		if (item) {
			onItemsChange([...items, item]);
		}

		if (clearOnAdd) {
			setQuery('');
		}
	};

	const handleRemoveItem = (index: number): void => {
		onItemsChange([...items.slice(0, index), ...items.slice(index + 1)]);
	};

	return (
		<div
			className={getCN(
				'search-input-list-root input-group-item',
				className,
				{disabled, focus}
			)}
		>
			<div className='form-control form-control-tag-group d-flex'>
				{items &&
					!!items.length &&
					items.map((item, i) => (
						<Label
							display='secondary'
							index={i}
							key={i}
							onRemove={handleRemoveItem}
						>
							{item}
						</Label>
					))}

				<BaseSelect
					className='flex-grow-1'
					containerClass={getCN(
						'search-input-list-container',
						containerClass
					)}
					dataSourceFn={dataSourceFn}
					disabled={disabled}
					emptyInputOnInactive
					inputSize='sm'
					inputValue={query}
					inset
					itemRenderer={itemRenderer}
					onBlur={() => setFocus(false)}
					onFocus={() => setFocus(true)}
					onInputValueChange={handleQueryChange}
					onSelect={handleAddItem}
					placeholder={placeholder}
					ref={_baseSelectRef}
				/>
			</div>
		</div>
	);
};

export default SearchInputList;
