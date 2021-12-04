import ClayIcon from '@clayui/icon';

const WarningBadge = ({children, ...props}) => {
	return (
		<div
			{...props}
			className="alert alert-danger ml-3 mr-3 p-sm-2 text-danger text-paragraph-sm"
		>
			<ClayIcon symbol="exclamation-full" />

			{children}
		</div>
	);
};

export default WarningBadge;
