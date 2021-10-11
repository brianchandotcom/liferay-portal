import { ClayInput } from '@clayui/form';
import ClayIcon from '@clayui/icon';

const SearchProject = ({ ...props }) => {
  return (
    <div className="position-relative">
      <ClayInput className="font-weight-semi-bold h5 rounded-pill search-project shadow-lg" {...props} type="text" />

      <ClayIcon className="position-absolute text-brand-primary" symbol="search" />
    </div>
  )
}

export default SearchProject;

