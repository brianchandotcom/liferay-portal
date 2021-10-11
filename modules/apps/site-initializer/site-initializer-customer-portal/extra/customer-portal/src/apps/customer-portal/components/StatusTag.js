import ClayLabel from '@clayui/label';
import { status } from '../utils/constants'

const StatusTag = ({ currentStatus }) => {
    switch (currentStatus) {
        case status.active: {
            return (<ClayLabel className="border-0 font-weight-normal m-0 px-2 text-capitalize text-paragraph-sm status-tag" displayType="success">Active</ClayLabel>);
        }
        case status.expired: {
            return (<div>Expired</div>);
        }
        default: {
            return (<div>No Status</div>);
        }
    }
};

export default StatusTag;