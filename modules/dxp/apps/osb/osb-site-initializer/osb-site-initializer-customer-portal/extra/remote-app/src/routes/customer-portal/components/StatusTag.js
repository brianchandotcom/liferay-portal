import ClayLabel from '@clayui/label';
import {status} from '../utils/constants';

const StatusTag = ({currentStatus}) => {
	if (currentStatus === status.active) {
		return (
			<ClayLabel
				className="border-0 font-weight-normal m-0 px-2 status-tag text-capitalize text-paragraph-sm"
				displayType="success"
			>
				Active
			</ClayLabel>
		);
	}

	if (currentStatus === status.expired) {
		return <div>Expired</div>;
	}

	return <div>No Status</div>;
};

export default StatusTag;
