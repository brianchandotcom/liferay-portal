@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Assert the No Results Found message on an Individual's Activities List
	As an Business User, I should be able to search an Individual's Activities List and see a "No Results Found" message

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search an Individual's Activities List with a query that won't return results
		Given I click "Abram Bauch" in the table
		When I search for "non-existent activity"
		Then I should see a message that there are no "Activities" found