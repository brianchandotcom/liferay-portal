@spira_Search @Search @Segments @List @team_FARO @priority_3
Feature: Search the Details of a Segment's Interest
	As an Business User, I should be able to search the details of an Interest within a Segment

	Background: [Setup] Navigate to an Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Search the Details of a Segment's Interest
		Given I click the "Interests" tab
		And I click "cutting-edge platforms" in the table
		When I search for "Tory Glover"
		Then I should only see an Individual named "Tory Glover" in the table