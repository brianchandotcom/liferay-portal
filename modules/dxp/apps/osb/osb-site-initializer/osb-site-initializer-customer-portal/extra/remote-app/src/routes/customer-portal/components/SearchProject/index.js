import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';

const SearchProject = ({onChange, props}) => {
	return (
		<div className="position-relative">
			<ClayInput
				className="font-weight-semi-bold h5 rounded-pill search-project shadow-lg"
				onChange={({target: {value}}) => onChange(value)}
				placeholder="Find a project"
				type="text"
				{...props}
			/>

			<ClayIcon
				className="position-absolute text-brand-primary"
				symbol="search"
			/>
		</div>
	);
};

export default SearchProject;
