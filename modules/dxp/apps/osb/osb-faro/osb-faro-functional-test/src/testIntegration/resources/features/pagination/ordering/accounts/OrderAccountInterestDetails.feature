@spira_Order @Order @Accounts @List @team_FARO @priority_3
Feature: Order the Details of an Account's Interest
	As an Business User, I should be able to sort the details of an Interest within a Segment

	Background: [Setup] Navigate to an Account
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Order the Details of an Account's Interest
		Given I click the "Interests" tab
		And I click "cutting-edge platforms" in the table
		When I sort the table by the "Name" column header in descending order
		Then I should see the users sorted in descending alphabetical order
		When I sort the table by the "Name" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order