@spira_Order @Order @Individuals @List @team_FARO @priority_3
Feature: Order the Individual's Interest List
	As an Business User, I should be able to sort an Individual's Interest List

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Order an Individual's Interest List
		Given I click "Abram Bauch" in the table
		And I click the "Interests" tab
		When I sort the table by the "Interest" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| mesh                     |
			| mesh synergistic schemas |
			| synergistic schemas      |
		When I sort the table by the "Interest" column header in descending order
		Then I should see the following ordered rows in column 1:
			| synergistic schemas      |
			| mesh synergistic schemas |
			| mesh                     |
