const labels = {
[$LABELS$]};

if (Liferay && Liferay.Language && Liferay.Language._cache) {
	Liferay.Language._cache = {
		...Liferay.Language._cache,
		...labels
	};
}

export default labels;