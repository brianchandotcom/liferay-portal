import ClayIcon from '@clayui/icon';

const ViewFilesPanel = ({sections = []}) => {
	if (!sections.length) {
		return null;
	}

	return (
		<>
			{sections.map((section, index) => {
				if (section.files.length) {
					return (
						<div className="view-section" key={index}>
							<h3>{section.title.toUpperCase()}</h3>

							<div className="content">
								{section.files.map((file, sectionIndex) => {
									if (file.type.includes('image')) {
										return (
											<div
												className="image"
												key={sectionIndex}
											>
												<img
													alt={file.name}
													src={file.fileURL}
												/>
											</div>
										);
									}

									return (
										<div
											className="documents"
											key={sectionIndex}
										>
											<ClayIcon
												className={file.icon}
												key={index}
												symbol={file.icon}
											/>
										</div>
									);
								})}
							</div>
						</div>
					);
				}
			})}
		</>
	);
};

export default ViewFilesPanel;
