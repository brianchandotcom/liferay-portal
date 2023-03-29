@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Search the Individual's Interest List
	As an Business User, I should be able to search an Individual's Interest List

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search an Individual's Interest List
		Given I click "Abram Bauch" in the table
		And I click the "Interests" tab
		When I search for "synergistic schemas"
		Then I should only see an Interest named "synergistic schemas" in the table