@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Search an Individual's Activities List
	As an Business User, I should be able to search an Individual's Activities List

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search an Individual's Activities List
		Given I click "Abram Bauch" in the table
		When I search for "mesh synergistic schemas"
		Then I should see an element exists in the table