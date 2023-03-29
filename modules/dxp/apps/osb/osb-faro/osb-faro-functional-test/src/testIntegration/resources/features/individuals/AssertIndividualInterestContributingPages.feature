@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert individual interest shows contributing pages
	As a Business User, I should be able to view contributing pages in individuals interest

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Assert contributing pages in individuals interest
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I click the "Interests" tab
		Then I should see "mesh" in a table row containing "1 Contributing Pages"