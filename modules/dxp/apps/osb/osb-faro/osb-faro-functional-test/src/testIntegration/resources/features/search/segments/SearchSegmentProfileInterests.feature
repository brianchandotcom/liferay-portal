@spira_Search @Search @Segments @List @Profile @team_FARO @priority_3
Feature: Search a Segment Profile's Interest
	As an Business User, I should be able to search a Segment Profile's Interests

	Background: [Setup] Navigate to a Segment Profile
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Search a Segment Profile's Interests
		Given I click the "Interests" tab
		When I search for "rich e-commerce"
		Then I should see the following ordered rows in the bar graph table:
			| rich e-commerce |