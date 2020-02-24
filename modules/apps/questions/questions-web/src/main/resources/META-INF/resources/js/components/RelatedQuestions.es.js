import React, {useContext, useEffect, useState} from 'react';
import {getRelatedThreads} from "../utils/client.es";
import {AppContext} from "../AppContext.es";
import {Link} from 'react-router-dom';

export default ({question}) => {

	const [relatedQuestions, setRelatedQuestions] = useState([]);
	const context = useContext(AppContext);

	useEffect(() => {
		if (question) {
			getRelatedThreads(question.headline, context.siteKey).then(
				data => setRelatedQuestions(
					data.items.filter(
						otherQuestion => otherQuestion.id !== question.id)));
		}
	}, [question, context.siteKey]);

	return (
		<>
			{!!relatedQuestions.length && (
				<>
					<h3>Related Questions</h3>
					<hr/>
					{
						relatedQuestions.map(relatedQuestion => (
							<ul key={relatedQuestion.id}>
								<li>
									<Link
										to={"/questions/" +
											relatedQuestion.id}>
										[{(relatedQuestion.aggregateRating &&
										   relatedQuestion.aggregateRating.ratingCount *
										   (relatedQuestion.aggregateRating.ratingAverage *
											2 - 1)) ||
										  0}] {relatedQuestion.headline}</Link>
								</li>
							</ul>
						))
					}
				</>
			)}
		</>
	);
};