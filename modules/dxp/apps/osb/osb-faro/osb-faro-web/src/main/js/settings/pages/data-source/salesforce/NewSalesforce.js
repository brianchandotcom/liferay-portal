import Authorization from './Authorization';
import {compose, withAdminPermission} from 'shared/hoc';

export default compose(withAdminPermission)(Authorization);
