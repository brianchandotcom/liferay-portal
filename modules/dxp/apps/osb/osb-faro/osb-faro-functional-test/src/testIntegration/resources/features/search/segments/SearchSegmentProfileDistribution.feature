@spira_Search @Search @Segments @List @team_FARO @priority_3
Feature: Search the Segment Profile Distribution
	As a Business User, I should be able to search a Segment Profile Distribution for an Individual

	Background: [Setup] Navigate to Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Search the Segments Profile Distribution
		Given I click the "Distribution" tab
		And I select breakdown "Individuals" by "gender"
		And I click row number "1" in the bar graph table
		When I search for "Annabell Feeney"
		Then I should only see an individual named "Annabell Feeney" in the table