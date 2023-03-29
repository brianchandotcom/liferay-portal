@spira_Pagination @Pagination @Segments @List @team_FARO @priority_3
Feature: Paginate list of members to be added to the segment
	As a Business User, I should be able to paginate to second page in members to be added

	Background: [Setup] Navigate to add members static segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button

	Scenario: Paginate to next page of members to be added
		Given I click the checkbox on the table row containing "Abram Bauch"
		And I click the checkbox on the table row containing "Alena Will"
		And I click the checkbox on the table row containing "Alton Jacobi"
		And I click the checkbox on the table row containing "Adrienne Johnston"
		And I click the checkbox on the table row containing "Alana Nicolas"
		And I click the checkbox on the table row containing "Alanna Emard"
		And I click the "Add" button
		And I click the "View Added Members" button
		When I set the pagination delta to "5"
		And I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see an individual named "Alton Jacobi" in the table