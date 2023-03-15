@spira_Order @Order @Segments @List @team_FARO @priority_3
Feature: Order a Dynamic Segment's Membership Preview Modal
	As a Business User, I should be able to order a Dynamic Individuals Segment's membership preview modal

	Background: [Setup] Begin creating a Dynamic Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown

	Scenario: Order the Dynamic Individuals Segment's Membership Preview
		Given I create a criteria with the following conditions:
			| additionalName | is | bert |
		And I click the Dynamic Segment membership preview
		When I sort the table by the "Name | Email" column header in descending order
		Then I should see the users sorted in descending alphabetical order
		When I sort the table by the "Name | Email" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order