@spira_Order @Order @Accounts @List @team_FARO @priority_3
Feature: Order the Accounts List
	As an Business User, I should be able to sort the Accounts List

	Background: [Setup] Go to the Accounts List page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Order the Accounts List
		Given I should see the "Accounts" page
		When I sort the table by the "Name" column header in descending order
		Then I should see the users sorted in descending alphabetical order
		When I sort the table by the "Name" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order