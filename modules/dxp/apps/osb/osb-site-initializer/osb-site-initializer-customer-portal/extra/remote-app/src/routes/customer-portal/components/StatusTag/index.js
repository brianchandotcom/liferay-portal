import ClayLabel from '@clayui/label';
import {status} from '../../utils/constants';

const labelProps = {
	[status.active]: {
		displayType: 'success',
		label: 'Active',
	},
	[status.expired]: {
		displayType: 'danger',
		label: 'Expired',
	},
	[status.future]: {
		displayType: 'info',
		label: 'Future',
	},
};

const StatusTag = ({currentStatus}) => {
	if (Object.values(status).includes(currentStatus)) {
		const labelProp = labelProps[currentStatus];

		return (
			<ClayLabel
				className={`font-weight-normal label-tonal-${labelProp.displayType} text-paragraph-sm`}
			>
				{labelProp.label}
			</ClayLabel>
		);
	}
};

export default StatusTag;
