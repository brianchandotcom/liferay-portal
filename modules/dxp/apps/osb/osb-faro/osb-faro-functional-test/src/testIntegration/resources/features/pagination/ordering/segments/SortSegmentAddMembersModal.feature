@spira_Order @Order @Segments @List @team_FARO @priority_3
Feature: Sort the Segment "Add Members" Modal
	As a Business User, I should be able to sort the Individuals list in the "Add Members" modal

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Sort the "Add Members" Modal by Name | Email
		Given I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		When I sort the table by the "Name | Email" column header in descending order
		Then I should see the users sorted in descending alphabetical order
		When I sort the table by the "Name | Email" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order
