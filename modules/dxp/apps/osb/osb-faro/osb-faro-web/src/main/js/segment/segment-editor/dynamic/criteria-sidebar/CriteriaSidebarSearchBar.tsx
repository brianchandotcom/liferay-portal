import React from 'react';
import SearchInput from 'shared/components/SearchInput';

interface ICriteriaSidebarSearchBarProps {
	onChange: (value: string) => void;
	searchValue: string;
}

const CriteriaSidebarSearchBar: React.FC<ICriteriaSidebarSearchBarProps> = ({
	onChange,
	searchValue
}) => (
	<div className='input-group'>
		<div className='input-group-item'>
			<SearchInput
				onChange={onChange}
				placeholder={Liferay.Language.get('search')}
				type='text'
				value={searchValue}
			/>
		</div>
	</div>
);

export default CriteriaSidebarSearchBar;
