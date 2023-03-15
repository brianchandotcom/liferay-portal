@spira_Search @Search @Segments @List @team_FARO @priority_3
Feature: Filter the Segment "Add Members" Modal
	As a Business User, I should be able to filter the Individuals list in the "Add Members" modal

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Filter the "Add Members" Modal by Name | Email
		Given I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		When I search for "John"
		Then I should see the users sorted in ascending alphabetical order
