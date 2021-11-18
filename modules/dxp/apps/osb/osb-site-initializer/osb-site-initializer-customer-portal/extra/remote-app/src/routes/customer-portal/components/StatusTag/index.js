import ClayLabel from '@clayui/label';
import {status} from '../../utils/constants';

const labelProps = {
	[status.active]: {displayType: 'success', label: 'Active'},
	[status.expired]: {displayType: 'danger', label: 'Expired'},
	[status.future]: {displayType: 'info', label: 'Future'},
};

const StatusTag = ({currentStatus}) => {
	if (Object.values(status).includes(currentStatus)) {
		const labelProp = labelProps[currentStatus];

		return (
			<ClayLabel
				className="border-0 font-weight-normal m-0 px-2 status-tag text-capitalize text-paragraph-sm"
				displayType={labelProp.displayType}
			>
				{labelProp.label}
			</ClayLabel>
		);
	}

	return <div>No Status</div>;
};

export default StatusTag;
