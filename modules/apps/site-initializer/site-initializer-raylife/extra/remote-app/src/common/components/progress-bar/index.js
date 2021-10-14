const ProgressBar = ({progress}) => {
	return (
		<div className="progress-bar" style={{height: 4, width: 144}}>
			<div
				className="progress-bar__progress"
				style={{
					width: `${progress}%`,
				}}
			/>
		</div>
	);
};

export default ProgressBar;
