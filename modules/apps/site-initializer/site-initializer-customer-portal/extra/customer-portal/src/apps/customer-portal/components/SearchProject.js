import { ClayInput } from '@clayui/form';
import ClayIcon from '@clayui/icon';

const SearchProject = ({ ...props }) => {
  return (
    <div className="position-relative">
      <ClayInput className={`search-project h5 font-weight-semi-bold rounded-pill shadow-lg`} {...props} type="text" />
      <ClayIcon className="position-absolute text-brand-primary" symbol="search" />
    </div>
  )
}

export default SearchProject;

